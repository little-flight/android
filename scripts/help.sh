if [ $# -eq 0 ]; then
  echo "help script can only be used with dev.sh"
  exit 2
fi

SCRIPTS_DIR=$1
shift

PRE_TEMPLATE="
Usage: dev <command> [options]\n
\nTips:
\n  - Run 'dev help' to see the list of available commands.
\n  - Run 'dev help <command>' to see command-specific help.
\n
"

__command_list() {
  echo $PRE_TEMPLATE
  echo "Available commands:"

  for cmd in $(ls $SCRIPTS_DIR/*.sh); do
    cmd=$(basename $cmd .sh)
    echo "  - $cmd"
  done
}

__docs() {
  COMMAND=$1
  shift

  SH_PATH="$SCRIPTS_DIR/$COMMAND.sh"
  DOC_PATH="$SCRIPTS_DIR/docs/$COMMAND.txt"

  if [ ! -f "$SH_PATH" ]; then
    echo "Unknown command: $COMMAND\n"
    exit 1
  fi

  if [ ! -f "$DOC_PATH" ]; then
    echo "command docs not found: $COMMAND"
    exit 1
  fi

  cat $DOC_PATH
  echo
  exit 0
}

if [ $# -eq 0 ]; then
  __command_list
  exit 0
elif [ "$1" == "help" ]; then
  shift
  __command_list
else
  __docs $1
fi

