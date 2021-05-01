import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {filter, takeUntil} from 'rxjs/operators';
import {CarDTO, UserDTO} from '@app/shared/models';
import {CarService} from '@app/core/services/car.service';
import {SelectItem} from 'primeng/api';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  carId: string;
  car: CarDTO;

  date1: Date;
  date2: Date;
  date3: Date;
  date4: Date;
  minDate: Date = new Date();
  defaultHour = new Date().getHours() + ':' + new Date().getMinutes();

  sortOptions: SelectItem[] = [
    {label: 'Opel', value: 'Opel'},
    {label: 'Ford', value: 'Ford'},
    {label: 'Renault', value: 'Renault'},
    {label: 'Toyota', value: 'Toyota'},
    {label: 'Volkswagen', value: 'Volkswagen'}
  ];

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

  getLoggedInUser(): void{
   const token: string = JSON.parse(localStorage.getItem('currentUser'))?.token;
   console.log(token);
   this.carService.getUserByJwt()
      .subscribe(
        async (data: UserDTO) => {
          console.log(data);
        },
        error => {
          console.error(error);
        });
  }

}
