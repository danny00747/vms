import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-verify-email',
  templateUrl: './verify-email.component.html',
  styleUrls: ['./verify-email.component.css']
})
export class VerifyEmailComponent implements OnInit {

  key = '';
  message = '';
  resendmessage = '';
  resendEmail = '';
  isVerified: boolean;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.key = this.route.snapshot.paramMap.get('key');
  }

}
