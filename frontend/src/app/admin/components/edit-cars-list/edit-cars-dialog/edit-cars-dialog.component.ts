import { Component, OnInit } from '@angular/core';
import {DynamicDialogRef} from 'primeng/dynamicdialog';
import {PrincingDetailsDTO} from '@app/shared/models';
import {PricingClassService} from '@app/core/services/pricing-class.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-edit-cars-dialog',
  templateUrl: './edit-cars-dialog.component.html',
  styleUrls: ['./edit-cars-dialog.component.css']
})
export class EditCarsDialogComponent implements OnInit {

  classes: PrincingDetailsDTO[];
  clickedClassName: PrincingDetailsDTO;

  className: string;
  dailyFine: number;
  priceByKm: number;
  allowedKmPerDay: number;
  costPerDay: number;

  constructor( public ref: DynamicDialogRef,
               private pricingClassService: PricingClassService) { }

  ngOnInit(): void {
    this.getAllPricingClasses();
  }

  getAllPricingClasses(): void {
    this.pricingClassService.getAllPricingClasses()
      .subscribe(
        async (data: PrincingDetailsDTO[]) => {
          this.classes = data;
        },
        error => {
          console.error(error);
        });
  }

  getClickedClass(event: any): void {
    this.clickedClassName = this.classes.find(x => x.className === event.id);
    this.dailyFine = this.clickedClassName.dailyFine;
    this.priceByKm = this.clickedClassName.priceByKm;
    this.allowedKmPerDay = this.clickedClassName.allowedKmPerDay;
    this.costPerDay = this.clickedClassName.costPerDay;
  }

  submit(): void {

    const updatedPricingClass = {
      className: this.clickedClassName.className,
      dailyFine: this.dailyFine,
      priceByKm: this.priceByKm,
      allowedKmPerDay: this.allowedKmPerDay,
      costPerDay: this.costPerDay,
    };

    const sub$: Observable<PrincingDetailsDTO> =
      this.pricingClassService.patchPricingClass(updatedPricingClass.className, updatedPricingClass);

    sub$.subscribe((e: PrincingDetailsDTO) => {
      this.ref.close(e);
    });
  }
}
