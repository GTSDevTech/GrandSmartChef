import { Routes } from '@angular/router';
import {authGuard} from "./guards/auth-guard/auth-guard";
import {profileEditGuard} from "./guards/profile-edit-guard/profile-edit-guard";



export const routes: Routes = [
  { path: '', redirectTo: 'starter', pathMatch: 'full' },
  {
    path: 'starter',
    loadComponent: () => import('./pages/starter/starter.page').then(m => m.StarterPage)
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.page').then(m => m.LoginPage),

  },
  {
    path: 'register',
    loadComponent: () => import('./pages/register/register.page').then(m => m.RegisterPage)
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.page').then(m => m.HomePage),
    canActivate: [authGuard]
  },
  {
    path: 'favorites',
    loadComponent: () => import('./pages/favorites/favorites.page').then(m => m.FavoritesPage),
    canActivate: [authGuard]
  },
  {
    path: 'shopping-cart',
    loadComponent: () => import('./pages/shopping-cart/shopping-cart.page').then(m => m.ShoppingCartPage),
    canActivate: [authGuard]
  },
  {
    path: 'history',
    loadComponent: () => import('./pages/history/history.page').then(m => m.HistoryPage),
    canActivate: [authGuard]
  },
  {
    path: 'profile',
    loadComponent: () => import('./pages/profile/profile.page').then(m => m.ProfilePage),
    canActivate: [authGuard]
  },
  {
    path: 'profile-edit',
    loadComponent: () => import('./pages/profile-edit/profile-edit.page').then(m => m.ProfileEditPage),
    canActivate: [authGuard],
    canDeactivate: [profileEditGuard]
  },
  {
    path: 'categories',
    loadComponent: () => import('./pages/ingredient-categories/categories.page').then(m => m.CategoriesPage)
  },
  {
    path: 'ingredients',
    loadComponent: () => import('./pages/ingredients/ingredients.page').then( m => m.IngredientsPage)
  },
  {
    path: 'post-create-account',
    loadComponent: () => import('./pages/post-create-account/post-create-account.page').then( m => m.PostCreateAccountPage)
  },
  {
    path: 'main-recipe/:id',
    loadComponent: () => import('./pages/recipe-detail/main-recipe.page').then(m => m.MainRecipePage),
    canActivate: [authGuard]
  },
  {
    path: 'contact',
    loadComponent: () => import('./pages/contact/contact.page').then( m => m.ContactPage)
  },
  {
    path: 'settings',
    loadComponent: () => import('./pages/settings/settings.page').then( m => m.SettingsPage)
  },
  {
    path: 'help',
    loadComponent: () => import('./pages/help/help.page').then( m => m.HelpPage)
  },
  {
    path: 'info',
    loadComponent: () => import('./pages/info/info.page').then( m => m.InfoPage)
  },
  {
    path: 'recipe-form',
    loadComponent: () =>
      import('./pages/admin/recipe-form/recipe-form.page')
        .then(m => m.RecipeFormPage)
  },
  {
    path: 'recipe-form/:id',
    loadComponent: () =>
      import('./pages/admin/recipe-form/recipe-form.page')
        .then(m => m.RecipeFormPage)
  },
  {
    path: 'recipe-home',
    loadComponent: () => import('./pages/admin/recipe-home/recipe-home.page').then( m => m.RecipeHomePage)
  },
  {
    path: 'create-recipe-card',
    loadComponent: () => import('./pages/admin/create-recipe-card/create-recipe-card.page').then( m => m.CreateRecipeCardPage)
  },


];
