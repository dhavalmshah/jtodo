import dayjs from 'dayjs';
import { IUserAttributes } from 'app/shared/model/user-attributes.model';
import { ITodo } from 'app/shared/model/todo.model';

export interface IComment {
  id?: number;
  content?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  user?: IUserAttributes | null;
  todo?: ITodo | null;
}

export const defaultValue: Readonly<IComment> = {};
