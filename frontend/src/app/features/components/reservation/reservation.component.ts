import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {CarDTO} from '@app/shared/models';
import {CarService} from '@app/core/services/car.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  carId: string;
  car: CarDTO;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private carService: CarService) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
    this.carId = this.route.snapshot.paramMap.get('carId');
    this.getCarDetails();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  getCarDetails(): void {
    this.carService.getOneCar(this.carId)
      .subscribe(
        async (data: CarDTO) => {
          this.car = data;
        },
        error => {
          console.error(error);
        });
  }

}
