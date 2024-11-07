import { TestBed } from '@angular/core/testing';

import { CitasAdminService } from './citas-admin.service';

describe('CitasAdminService', () => {
  let service: CitasAdminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CitasAdminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
