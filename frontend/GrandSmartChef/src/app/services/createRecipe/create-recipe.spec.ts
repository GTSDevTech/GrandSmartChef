import { TestBed } from '@angular/core/testing';

import { CreateRecipe } from './create-recipe';

describe('CreateRecipe', () => {
  let service: CreateRecipe;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateRecipe);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
