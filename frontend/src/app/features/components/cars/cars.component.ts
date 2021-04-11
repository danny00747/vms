import {Component, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {

  rangeValues: number[] = [75, 699];
  submitted = false;

  items: MenuItem[];
  pages: any[];

  constructor(private router: Router) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
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

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  range(): void {
    console.log(this.rangeValues);
  }

  activePage(value: HTMLElement): void {
    this.pages.forEach((x, i) => x.activePage = i === Number(value.id));
  }

}
