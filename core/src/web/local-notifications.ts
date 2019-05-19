import { WebPlugin } from './index';

import {
  LocalNotificationsPlugin,
  LocalNotificationEnabledResult,
  LocalNotificationPendingList,
  LocalNotificationActionType,
  LocalNotification,
  LocalNotificationScheduleResult
} from '../core-plugin-definitions';

import { PermissionsRequestResult } from '../definitions';

export class LocalNotificationsPluginWeb extends WebPlugin implements LocalNotificationsPlugin {
  constructor() {
    super({
      name: 'LocalNotifications',
      platforms: ['web']
    });
  }

  schedule(_options: { notifications: LocalNotification[]; }): Promise<LocalNotificationScheduleResult> {
    throw new Error("Method not implemented.");
  }

  getPending(): Promise<LocalNotificationPendingList> {
    throw new Error("Method not implemented.");
  }

  registerActionTypes(_options: { types: LocalNotificationActionType[]; }): Promise<void> {
    throw new Error("Method not implemented.");
  }

  cancel(_pending: LocalNotificationPendingList): Promise<void> {
    throw new Error("Method not implemented.");
  }

  areEnabled(): Promise<LocalNotificationEnabledResult> {
    throw new Error("Method not implemented.");
  }


  requestPermissions(): Promise<PermissionsRequestResult> {
    return new Promise((resolve, reject) => {
      Notification.requestPermission().then((result) => {
        if(result === 'denied') {
          reject(result);
          return;
        }
        resolve({
          results: [ result ]
        });
      }).catch((e) => {
        reject(e);
      });
    });
  }
}

const LocalNotifications = new LocalNotificationsPluginWeb();

export { LocalNotifications };
