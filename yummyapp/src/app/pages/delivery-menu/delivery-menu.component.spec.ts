import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryMenuComponent } from './delivery-menu.component';

describe('DeliveryMenuComponent', () => {
  let component: DeliveryMenuComponent;
  let fixture: ComponentFixture<DeliveryMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeliveryMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeliveryMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
