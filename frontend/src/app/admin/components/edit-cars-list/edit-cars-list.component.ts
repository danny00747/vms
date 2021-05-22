import {Component, Input, OnInit} from '@angular/core';
import {CarDTO, PrincingDetailsDTO, UserInfoDTO} from '@app/shared/models';
import {SelectItem} from 'primeng/api';
import {CarService} from '@app/core/services/car.service';
import {takeUntil} from 'rxjs/operators';
import {AddRentComponent} from '@app/admin/components/add-rent/add-rent.component';
import {DialogService} from 'primeng/dynamicdialog';
import {EditCarsDialogComponent} from '@app/admin/components/edit-cars-list/edit-cars-dialog/edit-cars-dialog.component';
import {PricingClassService} from '@app/core/services/pricing-class.service';
import {EToastSeverities, ToastService} from '@app/core/services';

@Component({
  selector: 'app-edit-cars-list',
  templateUrl: './edit-cars-list.component.html',
  styleUrls: ['./edit-cars-list.component.css']
})
export class EditCarsListComponent implements OnInit {

  @Input() users: UserInfoDTO[] = [];
  cars: CarDTO[] = [];
  cachedCars: CarDTO[] = [];
  sortOptions: SelectItem[];
  rating = 3;

  sortKey: string;
  sortKey1: string;
  carClassOptions: SelectItem[];
  sortField: string;

  sortOrder: number;


  constructor(private carService: CarService,
              public toastService: ToastService,
              private readonly dialogService: DialogService) {
  }

  ngOnInit(): void {
    this.getAllCars();
    this.sortOptions = [
      {label: 'Price High to Low', value: '!price'},
      {label: 'Price Low to High', value: 'price'},
    ];
    this.carClassOptions = [
      {label: 'A Class', value: 'CLASS_A'},
      {label: 'B Class', value: 'CLASS_B'},
      {label: 'C Class', value: 'CLASS_C'}
    ];
  }

  getAllCars(): void {
    this.carService.getAllCars()
      .subscribe(
        async (data: CarDTO[]) => {
          this.cars = data;
          this.cachedCars = data;
        },
        error => {
          console.error(error);
        });
  }

  onSortPriceChange(event: any): void {
    const value = event.value;
    if (value) {
      if (value.indexOf('!') === 0) {
        this.sortOrder = -1;
        this.sortField = 'modelDTO.princingDetailsDTO.costPerDay';
      } else {
        this.sortOrder = 1;
        this.sortField = 'modelDTO.princingDetailsDTO.costPerDay';
      }
    } else {
      this.cars = this.cachedCars;
    }
  }

  onSortClassChange(event: any): void {
    const value = event.value;

    if (value) {
      this.cars = this.cachedCars.filter(car => car.modelDTO.princingDetailsDTO.className === value);
    } else {
      this.cars = this.cachedCars;
    }
  }


  openEditCarClassRentDialog(): void {
    const ref = this.dialogService.open(EditCarsDialogComponent, {
      header: 'Pricing Details',
    });

    ref.onClose.subscribe((pricingClass: PrincingDetailsDTO) => {
      if (pricingClass) {
        this.toastService.show(EToastSeverities.SUCCESS, 'Successfully updated !');
        this.getAllCars();
      }
    });
  }

}
