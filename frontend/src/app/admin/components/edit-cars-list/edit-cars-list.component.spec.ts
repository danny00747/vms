import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCarsListComponent } from './edit-cars-list.component';

describe('EditCarsListComponent', () => {
  let component: EditCarsListComponent;
  let fixture: ComponentFixture<EditCarsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCarsListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCarsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
