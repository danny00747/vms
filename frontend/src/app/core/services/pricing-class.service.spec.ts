import { TestBed } from '@angular/core/testing';

import { PricingClassService } from './pricing-class.service';

describe('PricingClassService', () => {
  let service: PricingClassService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PricingClassService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
