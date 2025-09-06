package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.domain.Ingredient;
import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.*;
import com.goormthon.whattoeat.repository.IngredientRepository;
import com.goormthon.whattoeat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;

    public IngredientService(IngredientRepository ingredientRepository, MemberRepository memberRepository) {
        this.ingredientRepository = ingredientRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 레시피에 사용된 재료만큼 해당 사용자의 재고에서 차감
     *
     * @param recipeResponse gpt로부터 받은 응답
     */
    @Transactional
    public void consumeUsedIngredients(Long memberId, RecipeResponse recipeResponse) {
        if (recipeResponse == null) return;

        for (RecipeIngredientDto used : recipeResponse.getIngredients()) {
            String name = used.getName();
            int quantity = Integer.parseInt(used.getQuantity());
            String unit = used.getUnit();

            Ingredient stock = ingredientRepository.findByMember_IdAndIngredientName(memberId, name);

            int newQuantity = stock.getQuantity() - quantity;
            stock.updateQuantiy(newQuantity);
        }
    }

    @Transactional(readOnly = true)
    public List<IngredientListResponse> getIngredientList(Long memberId) {
        List<Ingredient> items = ingredientRepository.findByMember_IdOrderByExpirationDateAscIngredientNameAsc(memberId);

        return items.stream()
                .map(i -> new IngredientListResponse(
                        i.getId(),
                        i.getIngredientName(),
                        i.getQuantity(),
                        i.getUnit(),
                        i.getExpirationDate()
                ))
                .toList();
    }

    @Transactional
    public void createIngredient(Long memberId, IngredientCreateRequest ingredientCreateRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Ingredient newIngredient = ingredientCreateRequest.toEntity();

        ingredientRepository.save(newIngredient);
    }

    @Transactional
    public void updateIngredient(IngredientUpdateRequest ingredientUpdateRequest){
        Long ingredientId = ingredientUpdateRequest.getId();
        String newName = ingredientUpdateRequest.getName();
        int newQuantity = ingredientUpdateRequest.getQuantity();
        String newUnit = ingredientUpdateRequest.getUnit();
        Date newExpirationDate = ingredientUpdateRequest.getExprirationDate();

        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow();

        ingredient.update(newName, newQuantity, newUnit, newExpirationDate);

    }
}
