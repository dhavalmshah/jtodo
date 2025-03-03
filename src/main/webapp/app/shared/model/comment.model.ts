import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITodo } from 'app/shared/model/todo.model';

export interface IComment {
  id?: number;
  content?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  user?: IUser | null;
  todo?: ITodo | null;
}

export const defaultValue: Readonly<IComment> = {};
