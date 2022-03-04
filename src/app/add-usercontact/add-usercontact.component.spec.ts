import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUsercontactComponent } from './add-usercontact.component';

describe('AddUsercontactComponent', () => {
  let component: AddUsercontactComponent;
  let fixture: ComponentFixture<AddUsercontactComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUsercontactComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUsercontactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
