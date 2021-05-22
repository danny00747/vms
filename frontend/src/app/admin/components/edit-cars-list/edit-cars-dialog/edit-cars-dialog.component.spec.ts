import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCarsDialogComponent } from './edit-cars-dialog.component';

describe('EditCarsDialogComponent', () => {
  let component: EditCarsDialogComponent;
  let fixture: ComponentFixture<EditCarsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCarsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCarsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
