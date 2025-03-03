import dayjs from 'dayjs';
import { IProject } from 'app/shared/model/project.model';
import { IUser } from 'app/shared/model/user.model';
import { ITag } from 'app/shared/model/tag.model';
import { IAttachment } from 'app/shared/model/attachment.model';
import { IComment } from 'app/shared/model/comment.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';
import { TodoStatus } from 'app/shared/model/enumerations/todo-status.model';

export interface ITodo {
  id?: number;
  title?: string;
  description?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  priority?: keyof typeof Priority;
  status?: keyof typeof TodoStatus;
  project?: IProject | null;
  creator?: IUser | null;
  tags?: ITag[] | null;
  assignedUsers?: IUser[] | null;
  attachments?: IAttachment[] | null;
  comments?: IComment[] | null;
}

export const defaultValue: Readonly<ITodo> = {};
