import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MainRecipePage } from './main-recipe.page';

describe('MainRecipePage', () => {
  let component: MainRecipePage;
  let fixture: ComponentFixture<MainRecipePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(MainRecipePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
