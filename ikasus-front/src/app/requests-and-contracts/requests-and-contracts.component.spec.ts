import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestsAndContractsComponent } from './requests-and-contracts.component';

describe('RequestsAndContractsComponent', () => {
  let component: RequestsAndContractsComponent;
  let fixture: ComponentFixture<RequestsAndContractsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestsAndContractsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RequestsAndContractsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
