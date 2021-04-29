import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {ReplaySubject} from 'rxjs';
import {CarService} from '@app/core/services/car.service';
import {CarDTO} from '@app/shared/models';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit, OnDestroy {

  cars: CarDTO[] = [];
  cachedCars: CarDTO[] = [];
  rangeValues: number[] = [110, 249];
  minDate: Date = new Date();
  sortHeaders = [
    {name: 'All', active: true},
    {name: 'Ford', active: false},
    {name: 'Opel', active: false},
    {name: 'Toyota', active: false},
    {name: 'Volkswagen', active: false}
  ];

  date1: Date;
  date2: Date;

  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(private router: Router, private carService: CarService) {
    this.router.events
      .pipe(takeUntil(this.destroyed$),
        filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
    this.getCars();
  }

  @HostListener('window:beforeunload')
  async ngOnDestroy(): Promise<any> {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 10);
  }

  getCars(): void {
    this.carService.getCars()
      .pipe(takeUntil(this.destroyed$))
      .subscribe(
        async (data: CarDTO[]) => {
          this.cars = data;
          this.cachedCars = data;
        },
        error => {
          console.error(error);
        });
  }

  sortCars(sort: HTMLButtonElement): void {
    this.sortHeaders.forEach(x => x.active = x.name.toLocaleLowerCase() === sort.id);
    if (sort.id === 'all') {
      this.cars = this.cachedCars;
    } else {
      this.cars = this.cachedCars.filter(x => x.modelDTO.brand.toLocaleLowerCase() === sort.id);
    }
  }

  async submitSecondQuery(): Promise<void> {
    await this.router.navigate(['/cars'],
      {
        queryParams:
          {
            pickUpDate: this.date1.toISOString(),
            costRange: this.rangeValues
          }
      });
  }

  checkSubmission2(): boolean {
    return this.date2?.getTime() - this.date1?.getTime() > 1;
  }
}
