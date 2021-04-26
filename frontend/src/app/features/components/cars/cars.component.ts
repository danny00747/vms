import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {MenuItem, SelectItem} from 'primeng/api';
import {ReplaySubject} from 'rxjs';
import {CarService} from '@app/core/services/car.service';
import {EToastSeverities} from '@app/core/services';
import {CarDTO} from '@app/shared/models';
import {CarsResolver} from '@app/core/resolvers/cars.resolver';

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit, OnDestroy {

  rangeValues: number[] = [75, 699];
  submitted = false;

  items: MenuItem[];
  cars: CarDTO[] = [];
  carss: any = [];
  pages: any[];

  sortOptions: SelectItem[];

  // Observable used to notify subscription when to end
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);


  constructor(private router: Router,
              private carService: CarService,
              private routes: ActivatedRoute) {
    this.router.events
      .pipe(takeUntil(this.destroyed$),
        filter(event => event instanceof NavigationStart))
      .subscribe(() => {
        this.reload();
      });
  }

  ngOnInit(): void {

    this.getCars();

    this.sortOptions = [
      {label: 'Opel', value: 'Opel'},
      {label: 'Ford', value: 'Ford'},
      {label: 'Renault', value: 'Renault'},
      {label: 'Toyota', value: 'Toyota'},
      {label: 'Volkswagen', value: 'Volkswagen'}
    ];
    // this.cars = this.routes.snapshot.data.cars;
    // console.log(this.cars);
    this.carss = [
      {
        carId: 'c66045cf-c760-4157-8871-32fe27f15035',
        madeInYear: 2019,
        isDamaged: false,
        purchasedPrice: 12944,
        licensePlate: '4-BNK-341',
        modelDTO: {
          modelId: 'cce50279-e9d5-4bf0-a570-4073efd1ec68',
          modelType: 'C-Max',
          brand: 'Ford',
          modelOptionDTO: {
            optionCode: 'S5-CT-AF-B2',
            bagsNumber: 2,
            isAutomatic: false,
            hasAirConditioner: true,
            seatsNumber: 5
          }
        }
      },
      {
        carId: 'f25c9b76-c018-44bd-8af2-8088952c4661',
        madeInYear: 2019,
        isDamaged: false,
        purchasedPrice: 17944,
        licensePlate: '3-MPO-873',
        modelDTO: {
          modelId: '09077cdd-e80e-4a58-a4b9-948be2cf3b5e',
          modelType: 'Mondeo',
          brand: 'Ford',
          modelOptionDTO: {
            optionCode: 'S5-CT-AF-B2',
            bagsNumber: 2,
            isAutomatic: false,
            hasAirConditioner: true,
            seatsNumber: 5
          }
        },
      },
      {
        carId: 'f9e039a4-e5fe-40b8-9f19-b2d0985043dc',
        madeInYear: 2018,
        isDamaged: false,
        purchasedPrice: 24184,
        licensePlate: '3-QVW-23',
        modelDTO: {
          modelId: '78028974-a431-4965-a798-ff4b2a7d141d',
          modelType: 'Fiesta',
          brand: 'Ford',
          modelOptionDTO: {
            optionCode: 'S4-CT-AT-B2',
            bagsNumber: 2,
            isAutomatic: true,
            hasAirConditioner: true,
            seatsNumber: 4
          }
        },
      }
    ];

    /*
    this.routes.data.subscribe((response: any) => {
      console.log(response.cars);
    });
     */

    this.items = [
      {label: 'Step 1'},
      {label: 'Step 2'},
      {label: 'Step 2'},
      {label: 'Step 2'},
      {label: 'Step 2'},
      {label: 'Step 2'},
    ];
    this.pages = [
      {label: 'Step 1'},
      {label: 'Step 2'},
      {label: 'Step 2'},
      {label: 'Step 2'},
      {label: 'Step 2'},
      {label: 'Step 2'},
    ];
    this.pages.forEach((x, i) => x.activePage = (i === 0));
  }

  @HostListener('window:beforeunload')
  async ngOnDestroy(): Promise<any> {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  range(): void {
    console.log(this.rangeValues);
  }

  activePage(value: HTMLElement): void {
    this.pages.forEach((x, i) => x.activePage = i === Number(value.id));
  }

  getCars(): void {
    this.carService.getCars()
      .pipe(takeUntil(this.destroyed$))
      .subscribe(
        async (data) => {
          console.log(data);
          this.cars = data;
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
      this.getCars();
    }
  }

}
