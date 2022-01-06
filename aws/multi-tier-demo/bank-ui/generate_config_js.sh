#!/bin/sh -eu
if [ -z "${REST_API_BASE_NAME}" ]; then
    REST_API_BASE_NAME=undefined
else
    REST_API_BASE_NAME=${REST_API_BASE_NAME}
fi

 
cat <<EOF
window.REST_API_BASE_NAME="$REST_API_BASE_NAME"
EOF
