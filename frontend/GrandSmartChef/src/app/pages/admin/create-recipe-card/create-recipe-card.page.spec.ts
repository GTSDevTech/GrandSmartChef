import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateRecipeCardPage } from './create-recipe-card.page';

describe('CreateRecipeCardPage', () => {
  let component: CreateRecipeCardPage;
  let fixture: ComponentFixture<CreateRecipeCardPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateRecipeCardPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
