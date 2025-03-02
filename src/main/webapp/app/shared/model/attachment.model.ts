import dayjs from 'dayjs';
import { IUserAttributes } from 'app/shared/model/user-attributes.model';
import { ITodo } from 'app/shared/model/todo.model';

export interface IAttachment {
  id?: number;
  url?: string;
  type?: string;
  size?: number;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  name?: string;
  uploader?: IUserAttributes | null;
  todo?: ITodo | null;
}

export const defaultValue: Readonly<IAttachment> = {};
