import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIdentity, defaultValue } from 'app/shared/model/identity.model';

export const ACTION_TYPES = {
  FETCH_IDENTITY_LIST: 'identity/FETCH_IDENTITY_LIST',
  FETCH_IDENTITY: 'identity/FETCH_IDENTITY',
  CREATE_IDENTITY: 'identity/CREATE_IDENTITY',
  UPDATE_IDENTITY: 'identity/UPDATE_IDENTITY',
  DELETE_IDENTITY: 'identity/DELETE_IDENTITY',
  RESET: 'identity/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIdentity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type IdentityState = Readonly<typeof initialState>;

// Reducer

export default (state: IdentityState = initialState, action): IdentityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_IDENTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_IDENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_IDENTITY):
    case REQUEST(ACTION_TYPES.UPDATE_IDENTITY):
    case REQUEST(ACTION_TYPES.DELETE_IDENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_IDENTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_IDENTITY):
    case FAILURE(ACTION_TYPES.CREATE_IDENTITY):
    case FAILURE(ACTION_TYPES.UPDATE_IDENTITY):
    case FAILURE(ACTION_TYPES.DELETE_IDENTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_IDENTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_IDENTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_IDENTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_IDENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_IDENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/identities';

// Actions

export const getEntities: ICrudGetAllAction<IIdentity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_IDENTITY_LIST,
  payload: axios.get<IIdentity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IIdentity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_IDENTITY,
    payload: axios.get<IIdentity>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IIdentity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_IDENTITY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIdentity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_IDENTITY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIdentity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_IDENTITY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
