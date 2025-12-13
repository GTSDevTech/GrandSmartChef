import { TestBed } from '@angular/core/testing';

import { ShoppingProgressService } from './shopping-progress.service';

describe('ShoppingProgressService', () => {
  let service: ShoppingProgressService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShoppingProgressService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
