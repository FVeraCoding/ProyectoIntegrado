import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  show(message: string, type: 'success' | 'error' = 'success'): void {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.innerText = message;

    document.body.appendChild(notification);

    setTimeout(() => {
      notification.classList.add('visible');
    }, 10);

    setTimeout(() => {
      notification.classList.remove('visible');
      setTimeout(() => document.body.removeChild(notification), 300);
    }, 3000); 
  }
}
