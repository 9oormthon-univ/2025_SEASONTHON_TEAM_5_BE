package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.domain.Ingredient;
import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.IngredientListResponse;
import com.goormthon.whattoeat.dto.RecipeIngredientDto;
import com.goormthon.whattoeat.dto.RecipeResponse;
import com.goormthon.whattoeat.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    /**
     * 레시피에 사용된 재료만큼 해당 사용자의 재고에서 차감
     *
     * @param recipeResponse gpt로부터 받은 응답
     */
    @Transactional
    public void consumeUsedIngredients(Member member, RecipeResponse recipeResponse) {
        if (recipeResponse == null) return;

//        int userId = user.getId();
        int userId = 1;
        for (RecipeIngredientDto used : recipeResponse.getIngredients()) {
            String name = used.getName();
            int quantity = Integer.parseInt(used.getQuantity());
            String unit = used.getUnit();

            Ingredient stock = ingredientRepository.findByMember_IdAndIngredientName(userId, name);

            int newQuantity = stock.getQuantity() - quantity;
            stock.updateQuantiy(newQuantity);
        }
    }

    @Transactional(readOnly = true)
    public List<IngredientListResponse> getIngredientList(int memberId){
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
}
