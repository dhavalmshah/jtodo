import dayjs from 'dayjs';
import { ITodo } from 'app/shared/model/todo.model';

export interface ITag {
  id?: number;
  name?: string;
  color?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  todos?: ITodo[] | null;
}

export const defaultValue: Readonly<ITag> = {};
