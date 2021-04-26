import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {ReplaySubject, Subject} from 'rxjs';
import {strict} from 'assert';
import {CarService} from '@app/core/services/car.service';
import {CarDTO} from '@app/shared/models';

@Component({
  selector: 'app-car-detail',
  templateUrl: './car-detail.component.html',
  styleUrls: ['./car-detail.component.css']
})
export class CarDetailComponent implements OnInit, OnDestroy {

  carId: string;
  car: CarDTO;

  // Observable used to notify subscription when to end
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(private router: Router,
              private route: ActivatedRoute,
              private carService: CarService) {
    this.router.events
      .pipe(takeUntil(this.destroyed$),
        filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
    this.carId = this.route.snapshot.paramMap.get('id');
    console.log(this.carId);
    this.getCarDetails();
  }

  /*
    Stays subscribed until the component is about to be unload.
    A subject is used to notify a subscription to end
   */
  @HostListener('window:beforeunload')
  async ngOnDestroy(): Promise<any> {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  getCarDetails(): void {
    this.carService.getOneCar(this.carId)
      .pipe(takeUntil(this.destroyed$))
      .subscribe(
        async (data: CarDTO) => {
          this.car = data;
        },
        error => {
          console.error(error);
        });
  }
}
