import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarMaintenanceComponent } from './car-maintenance.component';

describe('CarMaintenanceComponent', () => {
  let component: CarMaintenanceComponent;
  let fixture: ComponentFixture<CarMaintenanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarMaintenanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarMaintenanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
