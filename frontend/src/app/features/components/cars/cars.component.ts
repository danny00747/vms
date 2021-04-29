import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {MenuItem, SelectItem} from 'primeng/api';
import {ReplaySubject} from 'rxjs';
import {CarService} from '@app/core/services/car.service';
import {CarDTO} from '@app/shared/models';

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit, OnDestroy {

  rangeValues: number[] = [110, 199];
  submitted = false;

  items: MenuItem[];
  cars: CarDTO[] = [];
  cachedCars: CarDTO[] = [];

  sortOptions: SelectItem[];

  withdrawalDate: string;
  queryParamCarBrand: string;
  queryParamAutomaticCar: string;
  queryParamRangesValues: any[] = [];

  nbOfPassengers: string;
  nbOfBags: string;

  // Observable used to notify subscription when to end
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);


  constructor(private router: Router,
              private carService: CarService,
              private route: ActivatedRoute) {
    this.router.events
      .pipe(takeUntil(this.destroyed$),
        filter(event => event instanceof NavigationStart))
      .subscribe(() => {
        this.reload();
      });
  }

  ngOnInit(): void {

    this.route.queryParams
      .subscribe(params => {
          this.withdrawalDate = params.pickUpDate;
          this.queryParamCarBrand = params.carBrand;
          this.queryParamAutomaticCar = params.automaticCar;
          this.queryParamRangesValues = params.costRange ?? [];
        }
      );

    this.getCars();

    this.sortOptions = [
      {label: 'Opel', value: 'Opel'},
      {label: 'Ford', value: 'Ford'},
      {label: 'Renault', value: 'Renault'},
      {label: 'Toyota', value: 'Toyota'},
      {label: 'Volkswagen', value: 'Volkswagen'}
    ];
  }

  @HostListener('window:beforeunload')
  async ngOnDestroy(): Promise<any> {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  getCars(): void {
    this.carService.getNoneBookedCars(this.withdrawalDate ?? new Date().toISOString())
      .pipe(takeUntil(this.destroyed$))
      .subscribe(
        async (data: CarDTO[]) => {
          console.log(data);
          if (this.queryParamCarBrand && this.queryParamCarBrand !== 'All') {
            this.cars = data.filter(x => x.modelDTO.brand === this.queryParamCarBrand);
            this.cachedCars = data;
          } else if (this.queryParamRangesValues.length !== 0) {
            this.cars = data
              .filter(x => x.modelDTO.princingDetailsDTO.costPerDay >= Number(this.queryParamRangesValues[0])
              && x.modelDTO.princingDetailsDTO.costPerDay <= Number(this.queryParamRangesValues[1]));
            this.cachedCars = data;
          } else {
            this.cars = data;
            this.cachedCars = data;
          }
        },
        error => {
          console.error(error);
        });
  }

  onSortChange(event): void {
    const value = event.value;
    if (value) {
      this.carService.getCarsByBrand(value)
        .pipe(takeUntil(this.destroyed$))
        .subscribe(
          async (data) => {
            this.cars = data;
          },
          error => {
            console.error(error);
          });
    } else {
      this.carService.getNoneBookedCars(new Date().toISOString())
        .pipe(takeUntil(this.destroyed$))
        .subscribe(
          async (data) => {
            this.cars = data;
          },
          error => {
            console.error(error);
          });
    }
  }

  applyFilters(): void {
    console.log(this.rangeValues);
    this.cars = this.cachedCars
      .filter(x => x.modelDTO.princingDetailsDTO.costPerDay >= this.rangeValues[0]
        && x.modelDTO.princingDetailsDTO.costPerDay <= this.rangeValues[1]);
  }

  getNbBags(bags: any): void {
    console.log(bags.checked, bags.id);
  }
}
