import {Injectable, TemplateRef} from '@angular/core';
import { MessageService } from 'primeng/api';

export enum EToastSeverities {
  SUCCESS = 'success',
  WARN = 'warn',
  ERROR = 'error',
  INFO = 'info'
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  toasts: any[] = [];

  constructor( private readonly toast: MessageService) {
  }


  show(severity: EToastSeverities, detail: string): void {
    this.toast.add({
      severity,
      detail,
    });
  }
}
