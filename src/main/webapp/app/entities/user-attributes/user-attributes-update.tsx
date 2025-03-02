import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedBlobField, ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntities as getTodos } from 'app/entities/todo/todo.reducer';
import { getEntities as getBadges } from 'app/entities/badge/badge.reducer';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { createEntity, getEntity, reset, updateEntity } from './user-attributes.reducer';

export const UserAttributesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const todos = useAppSelector(state => state.todo.entities);
  const badges = useAppSelector(state => state.badge.entities);
  const projects = useAppSelector(state => state.project.entities);
  const userAttributesEntity = useAppSelector(state => state.userAttributes.entity);
  const loading = useAppSelector(state => state.userAttributes.loading);
  const updating = useAppSelector(state => state.userAttributes.updating);
  const updateSuccess = useAppSelector(state => state.userAttributes.updateSuccess);

  const handleClose = () => {
    navigate('/user-attributes');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getTodos({}));
    dispatch(getBadges({}));
    dispatch(getProjects({}));
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
    values.emailVerified = convertDateTimeToServer(values.emailVerified);
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.updatedAt = convertDateTimeToServer(values.updatedAt);
    if (values.level !== undefined && typeof values.level !== 'number') {
      values.level = Number(values.level);
    }
    if (values.points !== undefined && typeof values.points !== 'number') {
      values.points = Number(values.points);
    }

    const entity = {
      ...userAttributesEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user?.toString()),
      assignedTodos: mapIdList(values.assignedTodos),
      badges: mapIdList(values.badges),
      projectMembers: mapIdList(values.projectMembers),
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
          emailVerified: displayDefaultDateTime(),
          createdAt: displayDefaultDateTime(),
          updatedAt: displayDefaultDateTime(),
        }
      : {
          ...userAttributesEntity,
          emailVerified: convertDateTimeFromServer(userAttributesEntity.emailVerified),
          createdAt: convertDateTimeFromServer(userAttributesEntity.createdAt),
          updatedAt: convertDateTimeFromServer(userAttributesEntity.updatedAt),
          user: userAttributesEntity?.user?.id,
          assignedTodos: userAttributesEntity?.assignedTodos?.map(e => e.id.toString()),
          badges: userAttributesEntity?.badges?.map(e => e.id.toString()),
          projectMembers: userAttributesEntity?.projectMembers?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jtodoApp.userAttributes.home.createOrEditLabel" data-cy="UserAttributesCreateUpdateHeading">
            Create or edit a User Attributes
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="user-attributes-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Name" id="user-attributes-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Email" id="user-attributes-email" name="email" data-cy="email" type="text" validate={{}} />
              <ValidatedField
                label="Email Verified"
                id="user-attributes-emailVerified"
                name="emailVerified"
                data-cy="emailVerified"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedBlobField label="Image" id="user-attributes-image" name="image" data-cy="image" isImage accept="image/*" />
              <ValidatedField
                label="Created At"
                id="user-attributes-createdAt"
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
                id="user-attributes-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Password" id="user-attributes-password" name="password" data-cy="password" type="text" />
              <ValidatedField
                label="Level"
                id="user-attributes-level"
                name="level"
                data-cy="level"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Points"
                id="user-attributes-points"
                name="points"
                data-cy="points"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="user-attributes-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Assigned Todos"
                id="user-attributes-assignedTodos"
                data-cy="assignedTodos"
                type="select"
                multiple
                name="assignedTodos"
              >
                <option value="" key="0" />
                {todos
                  ? todos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label="Badges" id="user-attributes-badges" data-cy="badges" type="select" multiple name="badges">
                <option value="" key="0" />
                {badges
                  ? badges.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Project Members"
                id="user-attributes-projectMembers"
                data-cy="projectMembers"
                type="select"
                multiple
                name="projectMembers"
              >
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-attributes" replace color="info">
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

export default UserAttributesUpdate;
