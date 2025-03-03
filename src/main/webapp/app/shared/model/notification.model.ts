import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { NotificationType } from 'app/shared/model/enumerations/notification-type.model';

export interface INotification {
  id?: number;
  content?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  read?: boolean;
  type?: keyof typeof NotificationType;
  user?: IUser | null;
}

export const defaultValue: Readonly<INotification> = {};
