import {Component, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {CarDTO} from '@app/shared/models';
import {CarService} from '@app/core/services/car.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  val3 = 3;
  rangeValues: number[] = [75, 699];
  rangeDates: Date[];
  minDate: Date = new Date();
  cars: CarDTO[] = [];
  smallCars: CarDTO[] = [];

  constructor(private router: Router, private carService: CarService) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
    this.getCars();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  getCars(): void {
    this.carService.getCars()
      .subscribe(
        async (data: CarDTO[]) => {
          this.cars = data.slice(0, 6);
          this.smallCars = data.slice(8, 12);
        },
        error => {
          console.error(error);
        });
  }

}
