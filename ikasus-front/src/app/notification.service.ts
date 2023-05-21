import { Injectable } from '@angular/core';
import { Routes } from './routes';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient, private router: Router) { }

  createNotification(notification: any) {
    return this.http.post(Routes.authorizedORIGIN + Routes.notifications, notification);
  }

  getAllNotifications() {
    return this.http.get<Notification[]>(Routes.authorizedORIGIN + Routes.notifications)
  }

  deleteNotification(notifId: number, rentalId: number) {
    return this.http.delete(Routes.authorizedORIGIN + Routes.notifications, {
      body: {
        id: {
          notificationId: notifId,
          rentalId: rentalId
        }
      }
    })
  }

  markSeen(notifId: number, rentalId: number) {
    return this.http.patch(Routes.authorizedORIGIN + Routes.notifications, {
      notificationId: notifId,
      rentalId: rentalId
    })
  }

  signal() {
    return this.http.post(Routes.authorizedORIGIN + Routes.signal, null);
  }
}

export interface Notification {
  id: number;
  rentalId: number;
  content: string;
  time: string;
  seen: boolean;
}
