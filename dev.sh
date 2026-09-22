CWD=$(pwd)
SCRIPTS_DIR="$CWD/scripts"
chmod +x $SCRIPTS_DIR/*.sh
chmod +x $CWD/*.sh
source $CWD/config.sh

__guide() {
  $SCRIPTS_DIR/help.sh $SCRIPTS_DIR $@
}

dev() {
  if [ $# -eq 0 ]; then
    __guide $@
    return 0
  fi

  COMMAND=$1
  shift

  if [ ! -f "$SCRIPTS_DIR/$COMMAND.sh" ]; then
    echo "Unknown command: $COMMAND\n"
    __guide $@
    return 1
  fi

  if [ "$COMMAND" = "help" ]; then
    __guide "$@"
    return $?
  fi

  bash "$SCRIPTS_DIR/$COMMAND.sh" "$@"
  return $?
}

