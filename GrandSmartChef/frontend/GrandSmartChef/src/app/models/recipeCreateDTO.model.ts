export interface RecipeCreateDTO {
  id?: number;
  name: string;
  difficulty: string;
  servings: number;
  prepTime: number;
  description: string;
  imageUrl: string;
  tags: { name: string }[];

  ingredients: {
    quantity: number;
    unit: string;
    ingredient: { id: number };
  }[];

  steps: {
    stepNumber: number;
    instruction: string;
  }[];
}
