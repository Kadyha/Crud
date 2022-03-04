import { TestBed, inject } from '@angular/core/testing';

import { UsercontactService } from './usercontact.service';

describe('UsercontactService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UsercontactService]
    });
  });

  it('should be created', inject([UsercontactService], (service: UsercontactService) => {
    expect(service).toBeTruthy();
  }));
});
