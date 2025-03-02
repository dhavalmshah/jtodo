import dayjs from 'dayjs';
import { IUserAttributes } from 'app/shared/model/user-attributes.model';
import { ITodo } from 'app/shared/model/todo.model';
import { NotificationType } from 'app/shared/model/enumerations/notification-type.model';

export interface INotification {
  id?: number;
  type?: keyof typeof NotificationType;
  content?: string;
  read?: boolean;
  createdAt?: dayjs.Dayjs;
  user?: IUserAttributes | null;
  task?: ITodo | null;
}

export const defaultValue: Readonly<INotification> = {
  read: false,
};
