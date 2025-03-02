import dayjs from 'dayjs';
import { ITag } from 'app/shared/model/tag.model';
import { IUserAttributes } from 'app/shared/model/user-attributes.model';
import { IProject } from 'app/shared/model/project.model';
import { TodoStatus } from 'app/shared/model/enumerations/todo-status.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';

export interface ITodo {
  id?: number;
  title?: string;
  description?: string | null;
  dueDate?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  status?: keyof typeof TodoStatus;
  priority?: keyof typeof Priority;
  pointsAwarded?: number | null;
  tags?: ITag[] | null;
  creator?: IUserAttributes | null;
  project?: IProject | null;
  parent?: ITodo | null;
  assignedUsers?: IUserAttributes[] | null;
}

export const defaultValue: Readonly<ITodo> = {};
