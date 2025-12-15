import { TestBed } from '@angular/core/testing';
import { CanMatchFn } from '@angular/router';

import { loadPageDataGuard } from './load-page-data-guard';

describe('loadPageDataGuard', () => {
  const executeGuard: CanMatchFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => loadPageDataGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
