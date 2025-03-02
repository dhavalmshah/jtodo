import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { getEntities as getUserAttributes } from 'app/entities/user-attributes/user-attributes.reducer';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { getEntities as getTodos } from 'app/entities/todo/todo.reducer';
import { TodoStatus } from 'app/shared/model/enumerations/todo-status.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';
import { createEntity, getEntity, updateEntity } from './todo.reducer';

export const TodoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tags = useAppSelector(state => state.tag.entities);
  const userAttributes = useAppSelector(state => state.userAttributes.entities);
  const projects = useAppSelector(state => state.project.entities);
  const todos = useAppSelector(state => state.todo.entities);
  const todoEntity = useAppSelector(state => state.todo.entity);
  const loading = useAppSelector(state => state.todo.loading);
  const updating = useAppSelector(state => state.todo.updating);
  const updateSuccess = useAppSelector(state => state.todo.updateSuccess);
  const todoStatusValues = Object.keys(TodoStatus);
  const priorityValues = Object.keys(Priority);

  const handleClose = () => {
    navigate('/todo');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTags({}));
    dispatch(getUserAttributes({}));
    dispatch(getProjects({}));
    dispatch(getTodos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.dueDate = convertDateTimeToServer(values.dueDate);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    if (values.pointsAwarded !== undefined && typeof values.pointsAwarded !== 'number') {
      values.pointsAwarded = Number(values.pointsAwarded);
    }

    const entity = {
      ...todoEntity,
      ...values,
      tags: mapIdList(values.tags),
      creator: userAttributes.find(it => it.id.toString() === values.creator?.toString()),
      project: projects.find(it => it.id.toString() === values.project?.toString()),
      parent: todos.find(it => it.id.toString() === values.parent?.toString()),
      assignedUsers: mapIdList(values.assignedUsers),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dueDate: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
        }
      : {
          status: 'TODO',
          priority: 'LOW',
          ...todoEntity,
          dueDate: convertDateTimeFromServer(todoEntity.dueDate),
          createdAt: convertDateTimeFromServer(todoEntity.createdAt),
          updatedAt: convertDateTimeFromServer(todoEntity.updatedAt),
          tags: todoEntity?.tags?.map(e => e.id.toString()),
          creator: todoEntity?.creator?.id,
          project: todoEntity?.project?.id,
          parent: todoEntity?.parent?.id,
          assignedUsers: todoEntity?.assignedUsers?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jtodoApp.todo.home.createOrEditLabel" data-cy="TodoCreateUpdateHeading">
            Create or edit a Todo
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="todo-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Title"
                id="todo-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Description" id="todo-description" name="description" data-cy="description" type="text" />
              <ValidatedField
                label="Due Date"
                id="todo-dueDate"
                name="dueDate"
                data-cy="dueDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Created At"
                id="todo-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Updated At"
                id="todo-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Status" id="todo-status" name="status" data-cy="status" type="select">
                {todoStatusValues.map(todoStatus => (
                  <option value={todoStatus} key={todoStatus}>
                    {todoStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Priority" id="todo-priority" name="priority" data-cy="priority" type="select">
                {priorityValues.map(priority => (
                  <option value={priority} key={priority}>
                    {priority}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Points Awarded" id="todo-pointsAwarded" name="pointsAwarded" data-cy="pointsAwarded" type="text" />
              <ValidatedField label="Tags" id="todo-tags" data-cy="tags" type="select" multiple name="tags">
                <option value="" key="0" />
                {tags
                  ? tags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="todo-creator" name="creator" data-cy="creator" label="Creator" type="select">
                <option value="" key="0" />
                {userAttributes
                  ? userAttributes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="todo-project" name="project" data-cy="project" label="Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="todo-parent" name="parent" data-cy="parent" label="Parent" type="select">
                <option value="" key="0" />
                {todos
                  ? todos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Assigned Users"
                id="todo-assignedUsers"
                data-cy="assignedUsers"
                type="select"
                multiple
                name="assignedUsers"
              >
                <option value="" key="0" />
                {userAttributes
                  ? userAttributes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/todo" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TodoUpdate;
