import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIdentityProfile, defaultValue } from 'app/shared/model/identity-profile.model';

export const ACTION_TYPES = {
  FETCH_IDENTITYPROFILE_LIST: 'identityProfile/FETCH_IDENTITYPROFILE_LIST',
  FETCH_IDENTITYPROFILE: 'identityProfile/FETCH_IDENTITYPROFILE',
  CREATE_IDENTITYPROFILE: 'identityProfile/CREATE_IDENTITYPROFILE',
  UPDATE_IDENTITYPROFILE: 'identityProfile/UPDATE_IDENTITYPROFILE',
  DELETE_IDENTITYPROFILE: 'identityProfile/DELETE_IDENTITYPROFILE',
  RESET: 'identityProfile/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIdentityProfile>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type IdentityProfileState = Readonly<typeof initialState>;

// Reducer

export default (state: IdentityProfileState = initialState, action): IdentityProfileState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_IDENTITYPROFILE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_IDENTITYPROFILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_IDENTITYPROFILE):
    case REQUEST(ACTION_TYPES.UPDATE_IDENTITYPROFILE):
    case REQUEST(ACTION_TYPES.DELETE_IDENTITYPROFILE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_IDENTITYPROFILE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_IDENTITYPROFILE):
    case FAILURE(ACTION_TYPES.CREATE_IDENTITYPROFILE):
    case FAILURE(ACTION_TYPES.UPDATE_IDENTITYPROFILE):
    case FAILURE(ACTION_TYPES.DELETE_IDENTITYPROFILE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_IDENTITYPROFILE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_IDENTITYPROFILE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_IDENTITYPROFILE):
    case SUCCESS(ACTION_TYPES.UPDATE_IDENTITYPROFILE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_IDENTITYPROFILE):
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

const apiUrl = 'api/identity-profiles';

// Actions

export const getEntities: ICrudGetAllAction<IIdentityProfile> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_IDENTITYPROFILE_LIST,
  payload: axios.get<IIdentityProfile>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IIdentityProfile> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_IDENTITYPROFILE,
    payload: axios.get<IIdentityProfile>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IIdentityProfile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_IDENTITYPROFILE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIdentityProfile> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_IDENTITYPROFILE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIdentityProfile> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_IDENTITYPROFILE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
