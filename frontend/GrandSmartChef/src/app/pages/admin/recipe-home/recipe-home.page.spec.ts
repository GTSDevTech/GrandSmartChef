import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RecipeHomePage } from './recipe-home.page';

describe('RecipeHomePage', () => {
  let component: RecipeHomePage;
  let fixture: ComponentFixture<RecipeHomePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeHomePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
