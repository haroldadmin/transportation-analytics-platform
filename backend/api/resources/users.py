from datetime import datetime

from flask import request
from flask_jwt_extended import create_access_token, jwt_required, get_jwt_identity
from flask_restplus import Namespace, Resource, marshal

import settings
from api.models.request import route_request_model
from api.models.user_model import add_models_to_namespace, user_login_request_model
from api.models.user_model import user_model, user_register_request_model
from api.resources.common import auth_header_parser
from database.models.user import UserModel

ns = Namespace("users", description="Operations related to users")
add_models_to_namespace(ns)


@ns.route("/all")
class UserCollection(Resource):

    @classmethod
    @jwt_required
    @ns.expect(auth_header_parser)
    @ns.marshal_with(user_model)
    def get(cls):
        return UserModel.query.all()


@ns.route("/")
class UserProfile(Resource):

    @classmethod
    @jwt_required
    @ns.expect(auth_header_parser)
    def get(cls):
        id = get_jwt_identity()
        user = UserModel.query.get(id)
        if not user:
            return {
                "message": "User not found"
            }, 404

        return marshal(user, user_model), 200


@ns.route("/<int:user_id>")
@ns.param("user_id", "The ID of the requested user")
class OtherUserProfile(Resource):

    @classmethod
    @jwt_required
    @ns.expect(auth_header_parser)
    def get(cls, user_id):
        user = UserModel.query.get(user_id)
        if not user:
            return {
                       "message": "User not found"
                   }, 404

        return marshal(user, user_model), 200


@ns.route("/<int:user_id>/requests")
class UserRouteRequestsCollection(Resource):

    @classmethod
    @jwt_required
    @ns.expect(auth_header_parser)
    @ns.marshal_list_with(route_request_model)
    def get(cls, user_id):
        user = UserModel.query.get(user_id)

        if not user:
            return {
                "message": "User not found"
            }, 404

        route_requests = user.requests
        return route_requests


@ns.route("/register")
class UserRegister(Resource):

    @classmethod
    @ns.expect(user_register_request_model, validate=True)
    def post(cls):
        data = request.json

        name = data["name"]
        email = data["email"]
        password = data["password"]

        if UserModel.query.filter_by(email=email).first() is not None:
            return {
                       "message": "User with this email address is already registered"
                   }, 400

        user = UserModel(name=name,
                         email=email,
                         password=password)

        user.save_to_db()

        return {
                   "message": "User registered successfully"
               }, 201


@ns.route("/login")
class UserLogin(Resource):

    @classmethod
    @ns.expect(user_login_request_model, validate=True)
    def post(cls):
        data = request.json

        email = data["email"]
        password = data["password"]

        user = UserModel.query.filter_by(email=email).first()

        if not user.check_password(password):
            return {
                       "message": "Invalid credentials"
                   }, 400

        access_token = create_access_token(identity=user.id)
        expiry_time = datetime.utcnow() + settings.JWT_ACCESS_TOKEN_EXPIRES

        return {
                   "access_token": access_token,
                   "expiry": expiry_time.timestamp()
               }, 200
