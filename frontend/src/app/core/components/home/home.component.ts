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
  date1: Date;
  date2: Date;
  minDate: Date = new Date();

  pickUpLocation: string;
  pickUpDate: Date;
  returnLocation: string;
  returnDate: Date;

  passengersButton = false;
  suitcasesButton = false;
  automaticButton = false;

  selectedCarBrand: string;

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

  getSelectedcarBrand(option: HTMLSelectElement): void {
    (option.value === 'All') ? this.selectedCarBrand = 'All' :
      (option.value === 'BMW') ? this.selectedCarBrand = 'BMW' :
        (option.value === 'Opel') ? this.selectedCarBrand = 'Opel' :
          (option.value === 'Audi') ? this.selectedCarBrand = 'Audi' :
            (option.value === 'Ford') ? this.selectedCarBrand = 'Ford' :
              (option.value === 'Volkswagen') ? this.selectedCarBrand = 'Volkswagen' :
                (option.value === 'Toyota') ? this.selectedCarBrand = 'Toyota' : this.selectedCarBrand = '';
  }

  passengersBtn(btn: any): void {
    this.passengersButton = btn.checked;
  }

  suitcasesBtn(btn: any): void {
    this.suitcasesButton = btn.checked;
  }

  automaticBtn(btn: any): void {
    this.automaticButton = btn.checked;
  }

  getSelectedPickUpLocations(option: HTMLSelectElement): void {
    (option.value === 'Brussels') ? this.pickUpLocation = 'Brussels' :
      (option.value === 'Namur') ? this.pickUpLocation = 'Namur' :
        (option.value === 'Genk') ? this.pickUpLocation = 'Genk' :
          (option.value === 'Ottignies-Louvain-la-Neuve') ? this.pickUpLocation = 'Ottignies-Louvain-la-Neuve' :
            (option.value === 'Mons') ? this.pickUpLocation = 'Mons' :
              (option.value === 'Bruges') ? this.pickUpLocation = 'Bruges' : this.pickUpLocation = '';
  }

  getSelectedReturnLocations(option: HTMLSelectElement): void {
    (option.value === 'Brussels') ? this.returnLocation = 'Brussels' :
      (option.value === 'Namur') ? this.returnLocation = 'Namur' :
        (option.value === 'Genk') ? this.returnLocation = 'Genk' :
          (option.value === 'Ottignies-Louvain-la-Neuve') ? this.returnLocation = 'Ottignies-Louvain-la-Neuve' :
            (option.value === 'Mons') ? this.returnLocation = 'Mons' :
              (option.value === 'Bruges') ? this.returnLocation = 'Bruges' : this.returnLocation = '';
  }

  getPickUpDate(pickUpDate: HTMLInputElement): void {
    this.pickUpDate = new Date(pickUpDate?.value);
    console.log(this.pickUpDate);
  }

  getReturnDate(returnDate: HTMLInputElement): void {
    this.returnDate = new Date(returnDate?.value);
    console.log(this.returnDate);
  }

  checkPickDate(): boolean {
    const today = new Date().getTime();
    const pickUpDate = this.pickUpDate?.getTime();
    return pickUpDate - today > 1;
  }

  checkReturnDate(): boolean {
    const today = new Date().getTime();
    const returnDate = this.returnDate?.getTime();
    return returnDate - today > 1 && this.returnDate.getTime() > this.pickUpDate.getTime();
  }

  async submitFirstQuery(): Promise<void> {
    console.log('suitcasesButton ', this.suitcasesButton);
    console.log('passengersButton ', this.passengersButton);
    console.log('automaticButton ', this.automaticButton);
    console.log('pickUpDate ', this.pickUpDate);
    console.log('returnLocation ', this.returnDate);
    console.log('selectedCarBrand ', this.selectedCarBrand);
    await this.router.navigate(['/cars'],
      {
        queryParams:
          {
            pickUpDate: this.returnDate.toISOString(),
            carBrand: this.selectedCarBrand,
            automaticCar: this.automaticButton
          }
      });
  }

  checkSubmission1(): boolean {
    return this.checkPickDate() && this.checkReturnDate();
  }
}
