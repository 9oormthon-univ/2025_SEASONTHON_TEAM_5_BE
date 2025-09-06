package com.goormthon.whattoeat.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.whattoeat.domain.Ingredient;
import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.RecipeRequest;
import com.goormthon.whattoeat.dto.RecipeDto;
import com.goormthon.whattoeat.dto.RecipeResponse;
import com.goormthon.whattoeat.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecipeService {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final IngredientRepository ingredientRepository;

    public RecipeService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper, IngredientRepository ingredientRepository) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
        this.ingredientRepository = ingredientRepository;
    }

    public List<RecipeDto> getRecipe(Member member, RecipeRequest request) {
        String template;
        try {
            template = new String(
                    new ClassPathResource("prompts/recipe_prompt.st").getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            ).replace("{", "\\{")
                    .replace("}", "\\}")
                    .replace("\\{ingredients\\}", "{ingredients}")
                    .replace("\\{cook_time_minutes\\}", "{cook_time_minutes}");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "템플릿 로딩 실패", e);
        }
        /*
        Todo
        userId로 재료 목록 가져오기
         */
        List<Ingredient> ingredientsList = ingredientRepository.findByMemberOrderByExpirationDateAscIngredientNameAsc(member);

        String ingredients = ingredientsList.stream()
                .map(ing -> ing.getIngredientName() + " " + ing.getQuantity() + ing.getUnit())
                .collect(Collectors.joining(", "));

//        String ingredients = "양파 1개, 감자 1개, 소금 20g, 돼지고기 400g, 카레가루 400g, 우유 400ml, 소시지 500g, 초콜릿 2kg, 케찹 912g";

        Map<String, Object> vars = new HashMap<>();
        vars.put("ingredients", ingredients);
        vars.put("cook_time_minutes", request.getCookTimeMinutes());

        var prompt = new PromptTemplate(template).create(vars);
        log.info("prompt: {}", prompt);

        String raw;
        try {
            raw = chatClient.prompt(prompt)
                    .call()
                    .content();

            raw = stripCodeFences(raw);
            log.info("OpenAI 답변: {}", raw);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "OpenAI 답변 로딩 실패", e);
        }

        List<RecipeDto> recipeResponse;
        try {
            recipeResponse = objectMapper.readValue(raw, new TypeReference<List<RecipeDto>>() {});
        } catch (Exception e) {
            log.warn("⚠️ OpenAI 응답 파싱 실패, 배열로 강제 감쌈. raw={}", raw);

            try {
                // []로 감싸고 다시 시도
                String forcedArray = "[" + raw + "]";
                recipeResponse = objectMapper.readValue(forcedArray, new TypeReference<List<RecipeDto>>() {});
            } catch (Exception inner) {
                log.error("❌ 배열 감싸기 후에도 파싱 실패. raw={}", raw, inner);
                recipeResponse = Collections.emptyList();
            }
        }

        return recipeResponse;

    }

    private String stripCodeFences(String s) {
        String trimmed = s.trim();
        if (trimmed.startsWith("```")) {
            int idx = trimmed.indexOf('\n');
            if (idx != -1) trimmed = trimmed.substring(idx + 1).trim();
            if (trimmed.endsWith("```")) {
                trimmed = trimmed.substring(0, trimmed.length() - 3).trim();
            }
        }
        return trimmed;
    }
}
