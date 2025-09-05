package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.domain.Ingredient;
import com.goormthon.whattoeat.domain.User;
import com.goormthon.whattoeat.dto.IngredientDto;
import com.goormthon.whattoeat.dto.RecipeResponse;
import com.goormthon.whattoeat.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void consumeUsedIngredients(User user, RecipeResponse recipeResponse) {
        if (recipeResponse == null) return;

//        int userId = user.getId();
        int userId = 1;
        for (IngredientDto used : recipeResponse.getIngredients()) {
            String name = used.getName();
            int quantity = Integer.parseInt(used.getQuantity());
            String unit = used.getUnit();

            Ingredient stock = ingredientRepository.findByUser_IdAndIngredientName(userId, name);

            int newQuantity = stock.getQuantity() - quantity;
            stock.updateQuantiy(newQuantity);
        }
    }
}
