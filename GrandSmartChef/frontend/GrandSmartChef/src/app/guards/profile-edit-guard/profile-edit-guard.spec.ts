import { TestBed } from '@angular/core/testing';
import { CanDeactivateFn } from '@angular/router';

import { profileEditGuard } from './profile-edit-guard';

describe('profileEditGuard', () => {
  const executeGuard: CanDeactivateFn<unknown> = (...guardParameters) => 
      TestBed.runInInjectionContext(() => profileEditGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
