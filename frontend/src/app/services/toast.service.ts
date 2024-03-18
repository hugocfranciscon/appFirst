import { Injectable, TemplateRef } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  private toasts: {
    textOrTpl: string | TemplateRef<any>;
    messageLink?: string;
    idButton?: string;
    classname?: string;
    delay?: number;
    autoHide: boolean;
  }[] = [];

  constructor() {}

  success(
    textOrTpl: string | TemplateRef<any>,
    messageLink?: string,
    idButton?: string,
    delay = 2800
  ) {
    const toastOptions = {
      classname: 'bg-success text-light',
      delay,
      autoHide: true,
    };

    this.toasts.push({ textOrTpl, messageLink, idButton, ...toastOptions });
  }

  danger(
    textOrTpl: string | TemplateRef<any>,
    messageLink?: string,
    idButton?: string,
    delay?: number
  ) {
    let options = {
      classname: 'bg-danger text-light',
      delay: delay,
      autoHide: true,
    };

    this.toasts.push({ textOrTpl, messageLink, idButton, ...options });
  }
}
