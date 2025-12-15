import { TestBed } from '@angular/core/testing';

import { ScrollFooterService } from './scroll-footer.service';

describe('ScrollFooterService', () => {
  let service: ScrollFooterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScrollFooterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
